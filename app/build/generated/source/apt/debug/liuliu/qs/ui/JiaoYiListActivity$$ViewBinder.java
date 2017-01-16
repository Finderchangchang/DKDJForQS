// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class JiaoYiListActivity$$ViewBinder<T extends liuliu.qs.ui.JiaoYiListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558532, "field 'mainLv'");
    target.mainLv = finder.castView(view, 2131558532, "field 'mainLv'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.mainLv = null;
  }
}
