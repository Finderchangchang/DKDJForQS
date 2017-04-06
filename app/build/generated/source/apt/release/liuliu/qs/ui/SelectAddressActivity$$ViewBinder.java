// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SelectAddressActivity$$ViewBinder<T extends liuliu.qs.ui.SelectAddressActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558551, "field 'sendAddressTv'");
    target.sendAddressTv = finder.castView(view, 2131558551, "field 'sendAddressTv'");
    view = finder.findRequiredView(source, 2131558622, "field 'selectPositionLl'");
    target.selectPositionLl = finder.castView(view, 2131558622, "field 'selectPositionLl'");
    view = finder.findRequiredView(source, 2131558545, "field 'mMapView'");
    target.mMapView = finder.castView(view, 2131558545, "field 'mMapView'");
    view = finder.findRequiredView(source, 2131558664, "field 'centerIv'");
    target.centerIv = finder.castView(view, 2131558664, "field 'centerIv'");
    view = finder.findRequiredView(source, 2131558552, "field 'address_title_tv'");
    target.address_title_tv = finder.castView(view, 2131558552, "field 'address_title_tv'");
    view = finder.findRequiredView(source, 2131558662, "field 'address_desc_tv'");
    target.address_desc_tv = finder.castView(view, 2131558662, "field 'address_desc_tv'");
    view = finder.findRequiredView(source, 2131558524, "field 'title_bar'");
    target.title_bar = finder.castView(view, 2131558524, "field 'title_bar'");
    view = finder.findRequiredView(source, 2131558550, "field 'search_poi_ll'");
    target.search_poi_ll = finder.castView(view, 2131558550, "field 'search_poi_ll'");
    view = finder.findRequiredView(source, 2131558663, "field 'save_address_btn'");
    target.save_address_btn = finder.castView(view, 2131558663, "field 'save_address_btn'");
    view = finder.findRequiredView(source, 2131558553, "field 'mp_et'");
    target.mp_et = finder.castView(view, 2131558553, "field 'mp_et'");
    view = finder.findRequiredView(source, 2131558626, "field 'location_iv'");
    target.location_iv = finder.castView(view, 2131558626, "field 'location_iv'");
  }

  @Override public void unbind(T target) {
    target.sendAddressTv = null;
    target.selectPositionLl = null;
    target.mMapView = null;
    target.centerIv = null;
    target.address_title_tv = null;
    target.address_desc_tv = null;
    target.title_bar = null;
    target.search_poi_ll = null;
    target.save_address_btn = null;
    target.mp_et = null;
    target.location_iv = null;
  }
}
