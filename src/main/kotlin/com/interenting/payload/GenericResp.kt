package com.interenting.payload

import com.fasterxml.jackson.annotation.JsonInclude

data class GenericResp(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    var message: String? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    var error: String? = null
)
