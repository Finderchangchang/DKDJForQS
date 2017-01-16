// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SearchPoiActivity$$ViewBinder<T extends liuliu.qs.ui.SearchPoiActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558661, "field 'inputEdittext'");
    target.inputEdittext = finder.castView(view, 2131558661, "field 'inputEdittext'");
    view = finder.findRequiredView(source, 2131558660, "field 'searchBarLayout'");
    target.searchBarLayout = finder.castView(view, 2131558660, "field 'searchBarLayout'");
    view = finder.findRequiredView(source, 2131558662, "field 'minputlist'");
    target.minputlist = finder.castView(view, 2131558662, "field 'minputlist'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.inputEdittext = null;
    target.searchBarLayout = null;
    target.minputlist = null;
  }
}
