// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FindPwdActivity$$ViewBinder<T extends liuliu.qs.ui.FindPwdActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558547, "field 'telTv'");
    target.telTv = finder.castView(view, 2131558547, "field 'telTv'");
    view = finder.findRequiredView(source, 2131558601, "field 'codeEt'");
    target.codeEt = finder.castView(view, 2131558601, "field 'codeEt'");
    view = finder.findRequiredView(source, 2131558602, "field 'getcodeBtn'");
    target.getcodeBtn = finder.castView(view, 2131558602, "field 'getcodeBtn'");
    view = finder.findRequiredView(source, 2131558525, "field 'pwdEt'");
    target.pwdEt = finder.castView(view, 2131558525, "field 'pwdEt'");
    view = finder.findRequiredView(source, 2131558526, "field 'pwdConfirmEt'");
    target.pwdConfirmEt = finder.castView(view, 2131558526, "field 'pwdConfirmEt'");
    view = finder.findRequiredView(source, 2131558603, "field 'regUserBtn'");
    target.regUserBtn = finder.castView(view, 2131558603, "field 'regUserBtn'");
    view = finder.findRequiredView(source, 2131558524, "field 'title_bar'");
    target.title_bar = finder.castView(view, 2131558524, "field 'title_bar'");
  }

  @Override public void unbind(T target) {
    target.telTv = null;
    target.codeEt = null;
    target.getcodeBtn = null;
    target.pwdEt = null;
    target.pwdConfirmEt = null;
    target.regUserBtn = null;
    target.title_bar = null;
  }
}
