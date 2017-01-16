// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegActivity$$ViewBinder<T extends liuliu.qs.ui.RegActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558533, "field 'telEt'");
    target.telEt = finder.castView(view, 2131558533, "field 'telEt'");
    view = finder.findRequiredView(source, 2131558659, "field 'sendCodeBtn'");
    target.sendCodeBtn = finder.castView(view, 2131558659, "field 'sendCodeBtn'");
    view = finder.findRequiredView(source, 2131558603, "field 'codeEt'");
    target.codeEt = finder.castView(view, 2131558603, "field 'codeEt'");
    view = finder.findRequiredView(source, 2131558525, "field 'pwdEt'");
    target.pwdEt = finder.castView(view, 2131558525, "field 'pwdEt'");
    view = finder.findRequiredView(source, 2131558526, "field 'pwdConfirmEt'");
    target.pwdConfirmEt = finder.castView(view, 2131558526, "field 'pwdConfirmEt'");
    view = finder.findRequiredView(source, 2131558534, "field 'loginBtn'");
    target.loginBtn = finder.castView(view, 2131558534, "field 'loginBtn'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.telEt = null;
    target.sendCodeBtn = null;
    target.codeEt = null;
    target.pwdEt = null;
    target.pwdConfirmEt = null;
    target.loginBtn = null;
  }
}
