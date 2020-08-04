/*
 * Copyright (c) 2020.
 * Martin Bartos
 * SmartHome BartOS
 * All rights reserved.
 */

package org.mabartos.services;

import io.quarkus.runtime.StartupEvent;
import org.mabartos.api.protocol.mqtt.MqttClientManager;
import org.mabartos.api.service.AppServices;
import org.mabartos.api.service.auth.AuthService;
import org.mabartos.api.service.capability.CapabilityService;
import org.mabartos.api.service.device.DeviceService;
import org.mabartos.api.service.home.HomeService;
import org.mabartos.api.service.room.RoomService;
import org.mabartos.api.service.user.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

@ApplicationScoped
public class DefaultAppServices implements AppServices {

    @Inject
    BeanManager beanManager;

    @Inject
    MqttClientManager mqttClientManager;

    @PersistenceContext
    EntityManager entityManager;

    public static Logger logger = Logger.getLogger(DefaultAppServices.class.getName());
    private ConcurrentMap<Class, Object> providers = new ConcurrentHashMap<>();

    @Inject
    public DefaultAppServices() {
    }

    public void start(@Observes StartupEvent start) {
        logger.info("App services are initialized");
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getProvider(Class<T> clazz) {
        return (T) providers.computeIfAbsent(clazz, item -> {
            Set<Bean<?>> beans = beanManager.getBeans(clazz);
            if (beans.iterator().hasNext()) {
                Bean<?> bean = beans.iterator().next();
                CreationalContext<?> context = beanManager.createCreationalContext(bean);
                return beanManager.getReference(bean, clazz, context);
            }
            return null;
        });
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public AuthService auth() {
        return getProvider(AuthService.class);
    }

    @Override
    public UserService users() {
        return getProvider(UserService.class);
    }

    @Override
    public HomeService homes() {
        return getProvider(HomeService.class);
    }

    @Override
    public RoomService rooms() {
        return getProvider(RoomService.class);
    }

    @Override
    public DeviceService devices() {
        return getProvider(DeviceService.class);
    }

    @Override
    public CapabilityService capabilities() {
        return getProvider(CapabilityService.class);
    }

    @Override
    public MqttClientManager mqttManager() {
        return mqttClientManager;
    }
}
