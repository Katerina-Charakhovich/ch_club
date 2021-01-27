package com.charakhovich.club.web.controller;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;
    private Map<String, String[]> requestParams = null;
    private static final String PARAM_NAME_REGEXP = "(?<=(name=\"))([\\s\\S]+?)(?=(\"?\\r\\n\\r\\n))";
    private static final String PARAM_VALUE_REGEXP = "(?<=\"?\\r\\n\\r\\n)([\\s\\S]+?)(?=(\\r\\n))";
    private static final String CONTENT_DISPOSITION = "Content-Disposition:\\sform-data;";
    private static final String PARAM_FILE_NAME = "filename";


    public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        if (cachedBytes == null)
            cacheInputStream();

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null)
            cacheInputStream();
        return new CachedServletInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private void cacheInputStream() throws IOException {
        /* Cache the inputstream in order to read it multiple times. For
         * convenience, use apache.commons IOUtils
         */
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    /* An inputstream which reads the cached request body */
    public class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream input;

        public CachedServletInputStream() {
            /* create a new input stream from the cached request body */
            input = new ByteArrayInputStream(cachedBytes.toByteArray());
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() throws IOException {
            return input.read();
        }
    }

    @Override
    public String getParameter(final String name) {
        if (getParameterMap().get(name) != null) {
            return getParameterMap().get(name)[0];
        } else {
            if (super.getParameter(name) != null) {
                getParameterMap().get(name)[0] = super.getParameter(name);
                requestParams.put(name, getParameterMap().get(name));
                return requestParams.get(name)[0];
            } else {
                return null;
            }
        }
    }

    /* create a map of parameters for Content-Disposition */
    public Map<String, String[]> getParameterMapFromContentDisposition() {
        Map<String, String[]> requestParams = new HashMap<>();
        String[] params = this.cachedBytes.toString().split(CONTENT_DISPOSITION);
        for (String param : params
        ) {
            if (!param.contains(PARAM_FILE_NAME)) {
                Pattern patternParamName = Pattern.compile(PARAM_NAME_REGEXP);
                Matcher matcherParamName = patternParamName.matcher(param);
                Pattern patternParamValue = Pattern.compile(PARAM_VALUE_REGEXP);
                Matcher matcherParamValue = patternParamValue.matcher(param);
                if (matcherParamName.find() && matcherParamValue.find()) {
                    String paramName = matcherParamName.group(0);
                    String paramValue = matcherParamValue.group(0);
                    requestParams.put(paramName, new String[]{paramValue});
                }
            }
        }
        return requestParams;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (requestParams == null) {
            if (cachedBytes == null) {
                requestParams = new HashMap<String, String[]>();
                requestParams.putAll(super.getParameterMap());
            } else {
                requestParams = getParameterMapFromContentDisposition();
            }
        }
        return Collections.unmodifiableMap(requestParams);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    @Override
    public String[] getParameterValues(final String name) {
        return getParameterMap().get(name);
    }
}