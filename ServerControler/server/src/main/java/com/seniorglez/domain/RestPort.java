package com.seniorglez.domain;

public abstract class RestPort {

    public void start() {
        mapMainEndpoint();
        mapPostMCEndpoint();
        mapPostRequestTokenEndpoint();
        mapPostUpdateEndpoint();
        mapPostGetLogsEndpoint();
        mapGetWorldEndpoint();
        mapGetLastLogLines();
    }

    protected abstract void mapMainEndpoint();

    protected abstract void mapPostMCEndpoint();

    protected abstract void mapPostRequestTokenEndpoint();

    protected abstract void mapPostUpdateEndpoint();

    protected abstract void mapPostGetLogsEndpoint();

    protected abstract void mapPostGetServerJSON();

    protected abstract void mapGetWorldEndpoint();

    protected abstract void mapGetLastLogLines();
    
}
