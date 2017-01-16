// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Manage_AddressActivity$$ViewBinder<T extends liuliu.qs.ui.Manage_AddressActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558553, "field 'sendAddressTv'");
    target.sendAddressTv = finder.castView(view, 2131558553, "field 'sendAddressTv'");
    view = finder.findRequiredView(source, 2131558554, "field 'addressTitleTv'");
    target.addressTitleTv = finder.castView(view, 2131558554, "field 'addressTitleTv'");
    view = finder.findRequiredView(source, 2131558552, "field 'searchPoiLl'");
    target.searchPoiLl = finder.castView(view, 2131558552, "field 'searchPoiLl'");
    view = finder.findRequiredView(source, 2131558555, "field 'mpEt'");
    target.mpEt = finder.castView(view, 2131558555, "field 'mpEt'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.sendAddressTv = null;
    target.addressTitleTv = null;
    target.searchPoiLl = null;
    target.mpEt = null;
  }
}
