package com.gbadegesin.encryption.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DecryptionRequest<T> {

    private String request;
    private Class<T> objectClass;

}
