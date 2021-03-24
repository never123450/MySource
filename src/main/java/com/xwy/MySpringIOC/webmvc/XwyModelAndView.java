package com.xwy.MySpringIOC.webmvc;

import java.util.Map;

/**
 * @param
 * @author xwy
 * @Description
 * @date 2021/3/24
 * @return
 */
public class XwyModelAndView {

    private String viewName;
    private Map<String, ?> model;

    public XwyModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
