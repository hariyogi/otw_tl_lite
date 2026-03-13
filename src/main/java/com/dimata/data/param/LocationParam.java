package com.dimata.data.param;

import org.jboss.resteasy.reactive.RestQuery;

public record LocationParam(
        @RestQuery String name
) {
}
