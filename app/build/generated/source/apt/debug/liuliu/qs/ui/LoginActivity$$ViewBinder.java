// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends liuliu.qs.ui.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'title_bar'");
    target.title_bar = finder.castView(view, 2131558524, "field 'title_bar'");
    view = finder.findRequiredView(source, 2131558533, "field 'telEt'");
    target.telEt = finder.castView(view, 2131558533, "field 'telEt'");
    view = finder.findRequiredView(source, 2131558525, "field 'pwdEt'");
    target.pwdEt = finder.castView(view, 2131558525, "field 'pwdEt'");
    view = finder.findRequiredView(source, 2131558534, "field 'loginBtn'");
    target.loginBtn = finder.castView(view, 2131558534, "field 'loginBtn'");
  }

  @Override public void unbind(T target) {
    target.title_bar = null;
    target.telEt = null;
    target.pwdEt = null;
    target.loginBtn = null;
  }
}
