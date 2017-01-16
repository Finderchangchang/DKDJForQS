// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AddressActivity$$ViewBinder<T extends liuliu.qs.ui.AddressActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558592, "field 'addAddressLl'");
    target.addAddressLl = finder.castView(view, 2131558592, "field 'addAddressLl'");
    view = finder.findRequiredView(source, 2131558532, "field 'mainLv'");
    target.mainLv = finder.castView(view, 2131558532, "field 'mainLv'");
    view = finder.findRequiredView(source, 2131558594, "field 'emptyTv'");
    target.emptyTv = finder.castView(view, 2131558594, "field 'emptyTv'");
    view = finder.findRequiredView(source, 2131558593, "field 'refreshRfl'");
    target.refreshRfl = finder.castView(view, 2131558593, "field 'refreshRfl'");
    view = finder.findRequiredView(source, 2131558524, "field 'title_bar'");
    target.title_bar = finder.castView(view, 2131558524, "field 'title_bar'");
  }

  @Override public void unbind(T target) {
    target.addAddressLl = null;
    target.mainLv = null;
    target.emptyTv = null;
    target.refreshRfl = null;
    target.title_bar = null;
  }
}
