package com.seniorglez.domain;

import com.seniorglez.domain.model.MessageErrors;
import com.seniorglez.domain.model.MessageSuccesses;
import com.seniorglez.functionalJava.monads.Result;

public interface Messages {
    public Result<MessageSuccesses, MessageErrors> send(String message);
}
