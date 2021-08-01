package adaptivex.pedidoscloud.View.EventListeners;

import android.content.Context;
import android.view.View;

/**
 * Created by Ezequiel on 06/04/2017.
 */

public class ButtonOnClickListener  implements  View.OnClickListener {
    private Context ctx;

    public ButtonOnClickListener(Context c){
        setCtx(c);
    }
    @Override
    public void onClick(View v) {




    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }
}
