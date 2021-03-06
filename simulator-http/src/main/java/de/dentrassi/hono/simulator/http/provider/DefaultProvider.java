/*******************************************************************************
 * Copyright (c) 2017, 2018 Red Hat Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jens Reimann - initial API and implementation
 *******************************************************************************/
package de.dentrassi.hono.simulator.http.provider;

import java.util.concurrent.Executor;

import de.dentrassi.hono.demo.common.EventWriter;
import de.dentrassi.hono.demo.common.Payload;
import de.dentrassi.hono.demo.common.Register;
import de.dentrassi.hono.simulator.http.Device;
import de.dentrassi.hono.simulator.http.DeviceProvider;
import de.dentrassi.hono.simulator.http.Statistics;
import okhttp3.OkHttpClient;

public class DefaultProvider implements DeviceProvider {

    @FunctionalInterface
    public interface Constructor {

        Device construct(Executor executor, String user, String deviceId, String tenant, String password,
                OkHttpClient client, Register register, Payload payload, Statistics telemetryStatistics,
                Statistics eventStatistics, EventWriter eventWriter);
    }

    private final String name;
    private final DefaultProvider.Constructor constructor;

    public DefaultProvider(final String name, final Constructor constructor) {
        this.name = name;
        this.constructor = constructor;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Device createDevice(final Executor executor, final String user, final String deviceId, final String tenant,
            final String password, final OkHttpClient client, final Register register, final Payload payload,
            final Statistics telemetryStatistics, final Statistics eventStatistics, final EventWriter eventWriter) {
        return this.constructor.construct(executor, user, deviceId, tenant, password, client, register, payload,
                telemetryStatistics, eventStatistics, eventWriter);
    }

}