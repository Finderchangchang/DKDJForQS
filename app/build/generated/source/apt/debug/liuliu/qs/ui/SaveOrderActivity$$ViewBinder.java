// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SaveOrderActivity$$ViewBinder<T extends liuliu.qs.ui.SaveOrderActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558545, "field 'mMapView'");
    target.mMapView = finder.castView(view, 2131558545, "field 'mMapView'");
    view = finder.findRequiredView(source, 2131558664, "field 'centerIv'");
    target.centerIv = finder.castView(view, 2131558664, "field 'centerIv'");
    view = finder.findRequiredView(source, 2131558524, "field 'title_bar'");
    target.title_bar = finder.castView(view, 2131558524, "field 'title_bar'");
    view = finder.findRequiredView(source, 2131558546, "field 'addressTv'");
    target.addressTv = finder.castView(view, 2131558546, "field 'addressTv'");
    view = finder.findRequiredView(source, 2131558547, "field 'telTv'");
    target.telTv = finder.castView(view, 2131558547, "field 'telTv'");
    view = finder.findRequiredView(source, 2131558548, "field 'shAddressTv'");
    target.shAddressTv = finder.castView(view, 2131558548, "field 'shAddressTv'");
    view = finder.findRequiredView(source, 2131558549, "field 'feiTv'");
    target.feiTv = finder.castView(view, 2131558549, "field 'feiTv'");
    view = finder.findRequiredView(source, 2131558544, "field 'payBtn'");
    target.payBtn = finder.castView(view, 2131558544, "field 'payBtn'");
    view = finder.findRequiredView(source, 2131558533, "field 'tel_et'");
    target.tel_et = finder.castView(view, 2131558533, "field 'tel_et'");
  }

  @Override public void unbind(T target) {
    target.mMapView = null;
    target.centerIv = null;
    target.title_bar = null;
    target.addressTv = null;
    target.telTv = null;
    target.shAddressTv = null;
    target.feiTv = null;
    target.payBtn = null;
    target.tel_et = null;
  }
}
