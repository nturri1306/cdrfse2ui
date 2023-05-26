package it.unidoc.cdr.core.ui.views.cdr;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.internal.Pair;
import de.codecamp.vaadin.components.messagedialog.MessageDialog;
import it.unidoc.cdr.core.code.XADCodesLoader;
import it.unidoc.cdr.core.helper.SlotHelper;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.*;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.ErrorData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.result.ResultData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.result.ResultDataWrapper;

import it.unidoc.cdr.core.ui.security.SecurityUtils;
import it.unidoc.cdr.core.ui.util.UIUtils;
import it.unidoc.cdr.core.ui.views.AbstractView;
import it.unidoc.repository.IDBRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author n.turri
 */
public abstract class CdrServiceView<T extends BaseData> extends AbstractView<T> {



    @Autowired
    public IDBRepository dbRepository;

    private DatePicker dpBegin;
    private DatePicker dpEnd;
    private Checkbox ckByCreation;

    Checkbox ckByError = new Checkbox();
    private Button btRemoveAll;

    public FilterHolder filterHolder = new FilterHolder();

    protected abstract ComponentEventListener<ClickEvent<Button>> createAction(Div header, Div content);

    @Override
    protected void initAppBar() { }

    protected void createFilterHeader(Grid.Column<?> col, HeaderRow header) {
        TextField field = createFilterHeader(col, value -> filterHolder.setFilter(col.getKey(), value), header);
        field.setTitle(col.getKey());

        // For text search for REST API...
        field.addKeyDownListener(ev -> {
            // Hot key for search in api rest
            if (ev.getKey().matches(Key.ENTER.toString())) {
                initManualSearch();
                refresh();
            }
        });

        // If value was cancelled
        field.addValueChangeListener(ev -> {
            if (ev.getValue().length() < 1 && ev.getOldValue().length() > 0) {
                initManualSearch();
                refresh();
            }
        });
    }

    @Override
    protected Component makeDetailsTool(T data, boolean isLocal) {
        Optional<UserDetails> user = SecurityUtils.getAuthenticatedUser();

        Button btRemove = UIUtils.createPrimaryButton(getTranslation("app.remove.title"));
        btRemove.setEnabled(user.isPresent() && SecurityUtils.isSuperUser(user.get()));
        btRemove.addClickListener(e -> {
            MessageDialog dlg = new MessageDialog().setTitle(
                    getTranslation("app.remove.question"),
                    VaadinIcon.QUESTION_CIRCLE.create());
            dlg.addButton().primary().
                    text(getTranslation("app.yes.title")).
                    onClick(ev -> {

                        try {
                            remove(Collections.singleton(data.getId()));

                            refresh();
                        } catch (Exception ex) {
                            UIUtils.showMessage(ex.getMessage());
                        }
                    }).closeOnClick();
            dlg.addButton().text(getTranslation("app.no.title")).
                    tertiary().
                    closeOnClick();
            dlg.open();
        });

        btRemove.setVisible(false);

        return btRemove;
    }

    protected FilterHolder getFilterHolder() {
        return filterHolder;
    }



    @Autowired
    private XADCodesLoader codes;

    public String getNameCodes(String classScheme) {
        try {
            return codes.getRoot().getCodeType(classScheme).getName();
        } catch (Exception ex) {
            return "";
        }
    }

    public String getAuthorPerson(List<ClassificationType> classifications, String scheme) {
        try {
            Optional<ClassificationType> cl = classifications.stream().filter(
                    c -> c.getClassificationScheme().equals(scheme)).findAny();

            if (cl.isPresent()) {
                Optional<SlotType> st = SlotHelper.firstItem(
                        cl.get().getSlots(), s -> s.getName().equals(FilterNames.AUTHORPERSON));

                if (st.isPresent()) {
                    Optional<String> v = SlotHelper.firstItem(st.get().getValues());

                    return v.isPresent() ? v.get() : "";
                }
            }
        } catch (Exception ex) { }

        return "";
    }

    public String getNodeRepresentation(List<ClassificationType> classifications, String scheme) {
        try {
            Optional<ClassificationType> cl = classifications.stream().filter(
                    c -> c.getClassificationScheme().equals(scheme)).findAny();

            if (cl.isPresent())
                return cl.get().getNodeRepresentation();
            else return "";
        } catch (Exception ex) {
            return "";
        }
    }

    protected Grid.Column getPatientIdTypeColumn(Grid<T> grid, ValueProvider<T, ?> valueProvider, String header, String key) {
        return grid.addColumn(new ComponentRenderer<>(item -> {
                    it.unidoc.cdr.core.ui.backend.rest.cdr.common.PatientIdType patientIdType = null;

                    if (Objects.nonNull(valueProvider.apply(item)))
                        patientIdType = (it.unidoc.cdr.core.ui.backend.rest.cdr.common.PatientIdType) valueProvider.apply(item);

                    return UIUtils.toSpan(patientIdType.getId(),
                            patientIdType.getUniversalId(),
                            patientIdType.getIdentifierTypeCode());
                })).setHeader(header)
                .setKey(key)
                .setResizable(true)
                .setSortable(true)
                .setAutoWidth(true);
    }


    @Override
    public void refresh() {
        closeDetails();

        executeQuery();
    }

    private void updateState(ResultData r) {
        updateState(Objects.nonNull(r) ? r.getTotalElements() : 0);
    }

    public void setInvisibleCkByCreation() {
        ckByCreation.setVisible(false);
    }

    protected abstract ResultData query(boolean asupdate, String begin, String end, RestPageRequest restPageRequest) throws Exception;

    protected abstract void remove(Set<String> items) throws Exception;

    @Override
    protected void selected(Set<T> items) {
        btRemoveAll.setVisible(items.size() > 1);
    }

    @Override
    protected String getDetailsHeader(T data) {
        return "";
    }
    @Override
    protected Component makeToolBar() {
        LocalDate currentDate = LocalDate.now();
        LocalDate oneMonthAgo = currentDate.minusMonths(1L);
        dpBegin = new DatePicker(oneMonthAgo);

        dpBegin.setLabel(getTranslation(Messages.APP_FILTER_FROMDATE));
        dpBegin.setRequired(true);
        dpEnd = new DatePicker(LocalDate.now());
        dpEnd.setLabel(getTranslation(Messages.APP_FILTER_TODATE));

        Button btSearch = UIUtils.createPrimaryButton(
                getTranslation(Messages.APP_FILTER_SEARCH), VaadinIcon.SEARCH);

        btSearch.addClickListener(e -> {
            if (dpBegin.getOptionalValue().isEmpty())
                UIUtils.showMessage(getTranslation(Messages.APP_FILTER_ERROR_FROMDATE));
            else if (dpEnd.getOptionalValue().isPresent() && dpEnd.getValue().isBefore(dpBegin.getValue()))
                UIUtils.showMessage(getTranslation(Messages.APP_FILTER_ERROR_GREATERTHAN));
            else {
                initManualSearch();
                refresh();
            }
        });

        Button btPreset = UIUtils.createContrastButton(VaadinIcon.CALENDAR_O);
        UIUtils.setTooltip(getTranslation(Messages.APP_FSEBROKER_FILTERPRESET_TITLE), btPreset);

        ContextMenu contextMenu = new ContextMenu(btPreset);
        contextMenu.setOpenOnClick(true);
        contextMenu.addItem(getTranslation(Messages.APP_FSEBROKER_TODAY_TITLE), e -> {
            LocalDate now = LocalDate.now();

            dpBegin.setValue(now);
            dpEnd.setValue(now);
            initManualSearch();
            refresh();
        });
        contextMenu.addItem(getTranslation(Messages.APP_FSEBROKER_YESTERDAY_TITLE), e -> {
            LocalDate date = LocalDate.now().minusDays(1);

            dpBegin.setValue(date);
            dpEnd.setValue(date);
            initManualSearch();
            refresh();
        });
        contextMenu.addItem(getTranslation(Messages.APP_FSEBROKER_LASTWEEK_TITLE), e -> {
            LocalDate now = LocalDate.now();

            dpBegin.setValue(now.minusDays(7));
            dpEnd.setValue(now);
            initManualSearch();
            refresh();
        });
        contextMenu.addItem(getTranslation(Messages.APP_FSEBROKER_CURRENTMONTH_TITLE), e -> {
            LocalDate now = LocalDate.now();

            dpBegin.setValue(now.withDayOfMonth(1));
            dpEnd.setValue(now);
            initManualSearch();
            refresh();
        });

        ckByCreation = new Checkbox();
        ckByCreation.setLabel(getTranslation(Messages.APP_CDR_VIEW_SEARCHBYCREATION));

        Optional<UserDetails> user = SecurityUtils.getAuthenticatedUser();

        btRemoveAll = UIUtils.createErrorButton(
                getTranslation( Messages.APP_FSEBROKER_SUBMIT_REMOVEALL), VaadinIcon.FILE_REMOVE);
        UIUtils.setTooltip(
                getTranslation(Messages.APP_FSEBROKER_SUBMIT_REMOVEALL_HELP), btRemoveAll);

        btRemoveAll.setVisible(false);
        btRemoveAll.setEnabled(user.isPresent() && SecurityUtils.isSuperUser(user.get()));
        btRemoveAll.addClickListener(e -> {
            Set<T> items = getItems();

            if (items != null) {
                MessageDialog dlg = new MessageDialog().setTitle(
                        getTranslation(Messages.APP_REMOVE_MANY_QUESTION, items.size()),
                        VaadinIcon.QUESTION_CIRCLE.create());

                dlg.getContent().add(new Span(getTranslation(Messages.APP_FSEBROKER_SUBMIT_REMOVEALL_HELP)));

                dlg.addButtonToLeft().text(getTranslation(Messages.APP_YES_TITLE)).primary().
                        onClick(ev -> {
                            try {
                                remove(items.stream().map(i -> i.getId()).collect(Collectors.toSet()));

                                refresh();
                            } catch (Exception ex) {
                                UIUtils.showMessage(ex.getMessage());
                            }
                        }).
                        closeOnClick();
                dlg.addButton().text(
                        getTranslation(Messages.APP_NO_TITLE)).tertiary().closeOnClick();
                dlg.open();
            } else
                UIUtils.showMessage(getTranslation(Messages.APP_ERROR_INTERNAL));
        });

        this.ckByError.setLabel("cerca con errore");

        HorizontalLayout toolBar = new HorizontalLayout();
        toolBar.setMargin(false);
        toolBar.setAlignItems(Alignment.BASELINE);
        toolBar.setWidthFull();
        toolBar.add(dpBegin);
        toolBar.add(dpEnd);
        toolBar.add(btSearch);
        toolBar.add(btPreset);
        toolBar.add(ckByError);
        toolBar.add(btRemoveAll);

        return toolBar;
    }

    protected void executeQuery() {
        Pair<String, String> pair = getDateFilter();
        if (Objects.isNull(pair.getFirst()))
            return;

        setItems(query -> {
            int page = query.getPage();

            if (isManualSearch())
                page = 0;

            try {
                RestPageRequest restPageRequest = new RestPageRequest();
                restPageRequest.setPage(page);
                restPageRequest.setSize(query.getPageSize());

                GridSortOrder<T> s = getSortOrder();
                if (Objects.nonNull(s)) {
                    restPageRequest.setProperty(s.getSorted().getKey());
                    restPageRequest.setAscending(SortDirection.ASCENDING.equals(s.getDirection()));
                }

                restPageRequest.setFilter(getFilterHolder().getFilterValues());

                asAutoSearch();

                ResultData r = query(!ckByCreation.getValue(), pair.getFirst(), pair.getSecond(), restPageRequest);

                updateState(r);

                if (Objects.isNull(r))
                    return Stream.empty();

                return ((ResultDataWrapper<T>)r).getContent().stream();
            } catch (Exception ex) {
                UIUtils.showMessage(ex.getMessage());
            }

            return Stream.empty();
        });
    }

    protected Pair<String, String> getDateFilter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate begin = Objects.nonNull(dpBegin.getValue()) ? dpBegin.getValue() : null;
        LocalDate end = Objects.nonNull(dpEnd.getValue()) ? dpEnd.getValue() : null;

        if (Objects.isNull(begin)) {
            UIUtils.showMessage(getTranslation(Messages.APP_CDR_FILTER_NOKEYS));

            return new Pair<>(null, null);
        }

        String beginParam = begin.format(formatter);
        String endParam = Objects.nonNull(end) ? end.format(formatter) : Strings.EMPTY;

        return new Pair<>(beginParam, endParam);
    }

    protected boolean isByCreation() {
        return ckByCreation.getValue();
    }

    protected boolean isByError() {
        return ((Boolean)this.ckByError.getValue()).booleanValue();
    }

    public TextArea getTextArea(String value, String strLabel) {
        TextArea textArea1 = new TextArea();
        textArea1.setLabel(strLabel);
        textArea1.setValue(UIUtils.toPrettyPrint(value));
        textArea1.setHeight("230px");
        textArea1.setWidth("100%");
        textArea1.getElement().getStyle().set("color", "#C71585");
        textArea1.setReadOnly(true);
        return textArea1;
    }

    public Object getField(Field field, Object data) {
        try {
            field.setAccessible(true);
            Object value = field.get(data);
            if (value instanceof Date) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy H:m:s", Locale.ITALIAN);
                String formattedDateTime = formatter.format(value);
                return formattedDateTime;
            }
            return field.get(data);
        } catch (Exception ex) {
            return null;
        }
    }

    protected <T> void addColumns(Grid<T> grid, Class<T> type, String collectionName) {
        Field[] fields1 = type.getSuperclass().getDeclaredFields();
        Field[] fields2 = type.getDeclaredFields();
        Field[] fields = new Field[fields1.length + fields2.length];
        System.arraycopy(fields1, 0, fields, 0, fields1.length);
        System.arraycopy(fields2, 0, fields, fields1.length, fields2.length);
        grid.addClassNames(new String[] { collectionName });
        grid.setSizeFull();
        HeaderRow header = grid.appendHeaderRow();
        for (Field field : fields) {
            if (field.getName().indexOf("_") != 0)
                if (!field.getName().equals("id")) {
                    Grid.Column<T> c = getColumn(data -> getField(field, data), field

                            .getName(), field.getName());
                    Comparator<T> comparator = (v1, v2) -> {
                        try {
                            Object f1 = field.get(v1);
                            Object f2 = field.get(v2);
                            if (f1 instanceof Integer && f2 instanceof Integer) {
                                Integer i1 = (Integer)f1;
                                Integer i2 = (Integer)f2;
                                return i1.compareTo(i2);
                            }
                            if (f1 instanceof String && f2 instanceof String) {
                                String s1 = (String)f1;
                                String s2 = (String)f2;
                                return s1.compareToIgnoreCase(s2);
                            }
                            if (f1 instanceof Date && f2 instanceof Date) {
                                Date d1 = (Date)f1;
                                Date d2 = (Date)f2;
                                return d1.compareTo(d2);
                            }
                            return 0;
                        } catch (Exception e) {
                            return 0;
                        }
                    };
                    c.setComparator(comparator);
                    if (field.isAnnotationPresent((Class) Annotations.AsToggleableGridColumn.class))
                        addColumnAsToggleable(c, field.getName());
                    if (field.isAnnotationPresent((Class)Annotations.AsFilterHeaderGridColumns.class))
                        createFilterHeader(c, header);
                    if (c.getKey().equals("statusCode")) {
                        c.setAutoWidth(false);
                        c.setWidth("10%");
                    }
                }
        }
    }
}

