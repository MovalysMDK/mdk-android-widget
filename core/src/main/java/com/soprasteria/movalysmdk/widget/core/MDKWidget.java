package com.soprasteria.movalysmdk.widget.core;

/**
 * Created by abelliard on 03/06/2015.
 */
public interface MDKWidget {

    void setRootId(int rootId);

    void setLabelId(int labelId);

    void setHelperId(int helperId);

    void setErrorId(int errorId);

    void setUseRootIdOnlyForError(boolean b);

    void setError(CharSequence error);
}
