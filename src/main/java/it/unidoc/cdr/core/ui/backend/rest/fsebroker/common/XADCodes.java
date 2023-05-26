package it.unidoc.cdr.core.ui.backend.rest.fsebroker.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author b.amoruso
 */
public class XADCodes {

    private String name;
    private String classScheme;
    private List<IHECode> codes = new ArrayList<>();

    public List<IHECode> getCodes() {
        return codes;
    }

    public void setCodes(List<IHECode> codes) {
        this.codes = codes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassScheme() {
        return classScheme;
    }

    public void setClassScheme(String classScheme) {
        this.classScheme = classScheme;
    }

    public static class IHECode {

        private String code;
        private String codingScheme;
        private String display;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDisplay() {
            return display;
        }

        public void setDisplay(String display) {
            this.display = display;
        }

        public String getCodingScheme() {
            return codingScheme;
        }

        public void setCodingScheme(String codingScheme) {
            this.codingScheme = codingScheme;
        }

        @Override
        public String toString() {
            return code + (display != null && display.length() > 0 &&
                    !display.equals(code) ? " (" + display + ")" : "");
        }

    }

}
