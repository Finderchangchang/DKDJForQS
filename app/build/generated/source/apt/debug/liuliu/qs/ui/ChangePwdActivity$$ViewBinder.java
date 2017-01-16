// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChangePwdActivity$$ViewBinder<T extends liuliu.qs.ui.ChangePwdActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558525, "field 'pwdEt'");
    target.pwdEt = finder.castView(view, 2131558525, "field 'pwdEt'");
    view = finder.findRequiredView(source, 2131558526, "field 'pwdConfirmEt'");
    target.pwdConfirmEt = finder.castView(view, 2131558526, "field 'pwdConfirmEt'");
    view = finder.findRequiredView(source, 2131558527, "field 'changeBtn'");
    target.changeBtn = finder.castView(view, 2131558527, "field 'changeBtn'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.pwdEt = null;
    target.pwdConfirmEt = null;
    target.changeBtn = null;
  }
}
