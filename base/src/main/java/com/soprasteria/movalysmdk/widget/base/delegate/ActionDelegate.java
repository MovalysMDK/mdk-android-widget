package com.soprasteria.movalysmdk.widget.base.delegate;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.soprasteria.movalysmdk.widget.base.R;
import com.soprasteria.movalysmdk.widget.core.command.Command;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetApplication;
import com.soprasteria.movalysmdk.widget.core.provider.MDKWidgetComponentProvider;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by abelliard on 05/06/2015.
 */
public class ActionDelegate {

    private final Class<? extends Command> primaryActionCommandClass;
    private final Class<? extends Command> secondaryActionCommandClass;
    private final WeakReference<View> weakView;
    private final int primaryActionViewId;
    private final int secondaryActionViewId;
    private final String qualifier;

    public ActionDelegate(View view, AttributeSet attrs) {

        this(view, attrs, null);

    }

    public ActionDelegate(View view, AttributeSet attrs, Class<? extends Command> primaryCommandClass) {

        this(view, attrs, primaryCommandClass, null);

    }

    public ActionDelegate(View view, AttributeSet attrs, Class<? extends Command> primaryCommandClass, Class<? extends Command> secondaryCommandClass) {

        this.weakView = new WeakReference<View>(view);
        this.primaryActionCommandClass = primaryCommandClass;
        this.secondaryActionCommandClass = secondaryCommandClass;

        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons_MDKButtonComponent);

        this.primaryActionViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKButtonComponent_primaryActionId, 0);
        this.secondaryActionViewId = typedArray.getResourceId(R.styleable.MDKCommons_MDKButtonComponent_secondaryActionId, 0);

        typedArray.recycle();

        typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.MDKCommons);

        this.qualifier = typedArray.getString(R.styleable.MDKCommons_qualifier);

        typedArray.recycle();

    }

    public void registerActions(View.OnClickListener listener)  {
        if (this.primaryActionViewId != 0) {
            View actionView = findActionView(this.primaryActionViewId);
            if (actionView != null) {
                actionView.setOnClickListener(listener);
            }
        }
        if (this.secondaryActionViewId != 0) {
            View actionView = findActionView(this.secondaryActionViewId);
            if (actionView != null) {
                actionView.setOnClickListener(listener);
            }
        }

    }

    private View findActionView(int buttonId) {
        View actionView = null;
        View v = this.weakView.get();
        if (v != null && v instanceof HasMdkDelegate) {
            View rootView = ((HasMdkDelegate) v).getMdkWidgetDelegate().findRootView(false);
            if (rootView != null) {
                actionView = rootView.findViewById(buttonId);

            }
        }
        return actionView;
    }


    private String baseKey(String widgetClassName, int id) {
        StringBuffer baseKey = new StringBuffer();

        baseKey.append(widgetClassName.toLowerCase());

        if (id == primaryActionViewId) {
            baseKey.append("_primary");
        } else {
            baseKey.append("_secondary");
        }

        baseKey.append("_command_class");

        return baseKey.toString();
    }

    private Command findCommandFrom(Context context, String baseKey) {

        String classPath = null;
        // case with qualifier
        if (this.qualifier != null) {
            classPath = findStringFromRessourceName(context, baseKey + "_" + this.qualifier);
            if (classPath == null) {
                Log.d("ActionDelegate", "no string resource define for :"+baseKey+ "_" + this.qualifier + " but qualifier was defined");
            }
        }
        // case without qualifier
        if (classPath == null) {
            classPath = findStringFromRessourceName(context, baseKey);
        }
        // create instance
        if (classPath == null) {
            throw new RuntimeException("no string resource define for :"+baseKey);
        }

        Command command = null;
        try {
            Class commandClass = Class.forName(classPath);
            Constructor constructor = commandClass.getConstructor(Context.class);
            command = (Command) constructor.newInstance(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        return command;
    }

    private String findStringFromRessourceName(Context context, String resourceStringName) {
        int resourceId = context.getResources().getIdentifier(resourceStringName, "string", context.getPackageName());
        if (resourceId != 0) {
            return context.getString(resourceId);
        }
        return null;
    }

    public Command<?,?> getAction(@IdRes int id) {

        Command<?,?> command = null;
        View v = this.weakView.get();
        if (v != null) {

            if (v.getContext().getApplicationContext() instanceof MDKWidgetApplication) {
                MDKWidgetComponentProvider widgetComponentProvider = ((MDKWidgetApplication) v.getContext().getApplicationContext()).getMDKWidgetComponentProvider();
                widgetComponentProvider.getCommand(v.getContext(), baseKey(v.getClass().getSimpleName(), id), this.qualifier);
            } else {
                command = this.findCommandFrom(v.getContext(), baseKey(v.getClass().getSimpleName(), id));
            }
        }

        return command;
    }
}
