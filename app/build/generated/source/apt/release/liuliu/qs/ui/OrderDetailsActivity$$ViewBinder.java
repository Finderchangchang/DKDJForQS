// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OrderDetailsActivity$$ViewBinder<T extends liuliu.qs.ui.OrderDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558650, "field 'tabFindFragmentTitle'");
    target.tabFindFragmentTitle = finder.castView(view, 2131558650, "field 'tabFindFragmentTitle'");
    view = finder.findRequiredView(source, 2131558651, "field 'orderListVp'");
    target.orderListVp = finder.castView(view, 2131558651, "field 'orderListVp'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.tabFindFragmentTitle = null;
    target.orderListVp = null;
  }
}
