package com.dsmupgrade.domain.field.api;

import com.dsmupgrade.IntegrationTest;
import com.dsmupgrade.domain.field.domain.FieldRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FieldApiTest extends IntegrationTest {

    @Autowired
    private FieldRepository fieldRepository;

    @Test
    public void 분야_리스트_가져오기() throws Exception {
        //when
        ResultActions resultActions = requestGetFields();

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    private ResultActions requestGetFields() throws Exception {
        return requestMvc(get("/fields"));
    }
}
