package org.witsml.parsing.instances.commons;

import java.util.Collection;

/**
 * @project witsml-parsing-instances
 * @created 2021-11-15 11:18
 * <p>
 * @author Alexander A. Kropotin
 */
public interface WITSMLConvertingService<Q, S> {

    S convert(Q request);
}
