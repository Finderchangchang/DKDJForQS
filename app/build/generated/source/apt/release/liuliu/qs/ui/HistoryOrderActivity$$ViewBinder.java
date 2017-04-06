// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HistoryOrderActivity$$ViewBinder<T extends liuliu.qs.ui.HistoryOrderActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'title_bar'");
    target.title_bar = finder.castView(view, 2131558524, "field 'title_bar'");
    view = finder.findRequiredView(source, 2131558528, "field 'order1Tv'");
    target.order1Tv = finder.castView(view, 2131558528, "field 'order1Tv'");
    view = finder.findRequiredView(source, 2131558529, "field 'order2Tv'");
    target.order2Tv = finder.castView(view, 2131558529, "field 'order2Tv'");
    view = finder.findRequiredView(source, 2131558530, "field 'order3Tv'");
    target.order3Tv = finder.castView(view, 2131558530, "field 'order3Tv'");
    view = finder.findRequiredView(source, 2131558532, "field 'mainLv'");
    target.mainLv = finder.castView(view, 2131558532, "field 'mainLv'");
  }

  @Override public void unbind(T target) {
    target.title_bar = null;
    target.order1Tv = null;
    target.order2Tv = null;
    target.order3Tv = null;
    target.mainLv = null;
  }
}
