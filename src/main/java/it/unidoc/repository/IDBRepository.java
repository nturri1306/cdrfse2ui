package it.unidoc.repository;


import com.vaadin.flow.internal.Pair;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.BaseFse2;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.CdrFse2Validation;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.MetadataFse2;
import java.util.List;

public interface IDBRepository {
    public static final String validationCollectionName = "cdr_fse2_validation";

    public static final String publishCollectionName = "cdr_fse2_publish";

    public static final String updateCollectionName = "cdr_fse2_update";

    public static final String metadataName = "cdr_fse2_metadata";

    public static final String traceidstatusCollectionName = "cdr_fse2_traceidstatus";

    public static final String workflowinstanceidstatusCollectionName = "cdr_fse2_workflowinstanceidstatus";

    public static final String deletedCollectionName = "cdr_fse2_delete";

    List<BaseFse2> getBaseItems(String paramString, Pair<String, String> paramPair, int paramInt) throws Exception;

    List<CdrFse2Validation> getValidationItems(String paramString, Pair<String, String> paramPair, int paramInt) throws Exception;

    List<MetadataFse2> getMetadataItems(String paramString, Pair<String, String> paramPair, int paramInt) throws Exception;
}
