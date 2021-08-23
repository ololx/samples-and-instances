package org.spring.boot.metrics.instances.some.api.model.detail;

import org.spring.boot.metrics.instances.some.api.model.SomeApiModel;

/**
 * The interface Some api detail.
 *
 * @author Alexander A. Kropotin
 * @project some -api
 * @created 2021 -07-06 17:52 <p>
 */
public interface SomeApiDetail extends SomeApiModel {

    /**
     * The interface Create.
     */
    interface Create {}

    /**
     * The interface Retrieve.
     */
    interface Retrieve {}

    /**
     * The interface Update.
     */
    interface Update {}

    /**
     * The interface Delete.
     */
    interface Delete {}
}
