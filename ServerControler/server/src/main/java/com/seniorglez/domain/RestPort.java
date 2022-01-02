package com.seniorglez.domain;

public abstract class RestPort {

    public void start() {
        mapPostMCEndpoint();
        mapPostRequestTokenEndpoint();
        mapPostUpdateEndpoint();
        mapPostGetLogsEndpoint();
        mapGetWorldEndpoint();
    }

    protected abstract void mapPostMCEndpoint();

    protected abstract void mapPostRequestTokenEndpoint();

    protected abstract void mapPostUpdateEndpoint();

    protected abstract void mapPostGetLogsEndpoint();

    protected abstract void mapGetWorldEndpoint();
    
}
