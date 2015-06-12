package com.soprasteria.movalysmdk.widget.sample;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lmichenaud on 12/06/2015.
 */
public class AndroidJacocoTestRunner extends AndroidJUnitRunner {

    static {
        System.setProperty("jacoco-agent.destfile", "/data/data/com.soprasteria.movalysmdk.widget.sample/coverage.ec");
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        try {
            Class rt = Class.forName("org.jacoco.agent.rt.RT");
            Method getAgent = rt.getMethod("getAgent");
            Method dump = getAgent.getReturnType().getMethod("dump", boolean.class);
            Object agent = getAgent.invoke(null);
            dump.invoke(agent, false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        super.finish(resultCode, results);
    }
}
