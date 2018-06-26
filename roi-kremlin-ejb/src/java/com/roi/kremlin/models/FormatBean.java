package com.roi.kremlin.models;

import com.roi.models.LoggerBean;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.EJB;

@Stateless
@LocalBean
public class FormatBean {

    @EJB
    LoggerBean loggerBean;

    public boolean validateFormat(Object value, ParameterSpecification paramSpec) {
        boolean isDate = (Date.class).isInstance(value);
        if (isDate) {
            try {
                String format = paramSpec.getFormat();
                DateFormat dateFormat = new SimpleDateFormat(paramSpec.getFormat());
                dateFormat.parse(value.toString());
                return true;
            } catch (IllegalArgumentException ex) {
                loggerBean.logInputErrorFromClass("Inválid format specified: " + ex,
                        ValidatorBean.class.toString());
                return false;
            } catch (ParseException ex) {
                loggerBean.logInputErrorFromClass("Couldnt re-format string: " + ex,
                        ValidatorBean.class.toString());
                return false;
            }
        }
        return true;
    }

    public boolean specifiedFormatisValid(ParameterSpecification paramSpec) {
        boolean formatWasSpecified = paramSpec.getFormat() != null;
        if (!formatWasSpecified) {
            return true;
        }
        boolean isDate = "Date".equalsIgnoreCase(paramSpec.getType());
        if (isDate) {
            try {
                String format = paramSpec.getFormat();
                DateFormat dateFormat = new SimpleDateFormat(paramSpec.getFormat());
                return true;
            } catch (IllegalArgumentException ex) {
                loggerBean.logInputErrorFromClass("Inválid format specified: " + ex,
                        ValidatorBean.class.toString());
                return false;
            }
        }
        return false;
    }
}
