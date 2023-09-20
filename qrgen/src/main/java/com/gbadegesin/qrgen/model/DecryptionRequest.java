package com.gbadegesin.qrgen.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DecryptionRequest<T> {

    private String request;
    private Class<T> objectClass;

}
