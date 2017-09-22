package test.ceshi.titlebar_gradient;

import android.support.annotation.IntDef;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * Created by zj on 2017/9/21.
 */

public class ItemModel implements Serializable {
    @Type
    public int type;
    public Object data;

    public ItemModel(@Type int type, Object data) {
        this.type = type;
        this.data = data;
    }

    @IntDef({
            Type.IMAGE,
            Type.TEXT
    })
    public @interface Type {

        int IMAGE = 1001;
        int TEXT = 1002;
    }
}
