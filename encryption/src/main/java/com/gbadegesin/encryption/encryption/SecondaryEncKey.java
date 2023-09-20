package com.gbadegesin.encryption.encryption;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
@Scope("singleton")
public class SecondaryEncKey {

    private String secondaryEncKey;

}
