package org.spring.boot.distributed.tracing.instances.service.b.model.detail;

import org.spring.boot.distributed.tracing.instances.service.b.model.ServiceBModel;

/**
 * The interface Service b detail.
 *
 * @author Alexander A. Kropotin
 * @project service -b
 * @created 2021 -08-04 12:20 <p>
 */
public interface ServiceBDetail extends ServiceBModel {

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
