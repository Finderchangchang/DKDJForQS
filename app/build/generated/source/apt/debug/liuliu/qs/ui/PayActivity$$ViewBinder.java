// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PayActivity$$ViewBinder<T extends liuliu.qs.ui.PayActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558539, "field 'btn1'");
    target.btn1 = finder.castView(view, 2131558539, "field 'btn1'");
    view = finder.findRequiredView(source, 2131558540, "field 'btn2'");
    target.btn2 = finder.castView(view, 2131558540, "field 'btn2'");
    view = finder.findRequiredView(source, 2131558541, "field 'btn3'");
    target.btn3 = finder.castView(view, 2131558541, "field 'btn3'");
    view = finder.findRequiredView(source, 2131558542, "field 'btn4'");
    target.btn4 = finder.castView(view, 2131558542, "field 'btn4'");
    view = finder.findRequiredView(source, 2131558543, "field 'edit'");
    target.edit = finder.castView(view, 2131558543, "field 'edit'");
    view = finder.findRequiredView(source, 2131558544, "field 'payBtn'");
    target.payBtn = finder.castView(view, 2131558544, "field 'payBtn'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.btn1 = null;
    target.btn2 = null;
    target.btn3 = null;
    target.btn4 = null;
    target.edit = null;
    target.payBtn = null;
  }
}
