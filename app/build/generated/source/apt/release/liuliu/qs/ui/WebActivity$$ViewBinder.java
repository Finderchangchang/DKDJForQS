// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebActivity$$ViewBinder<T extends liuliu.qs.ui.WebActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558677, "field 'webView'");
    target.webView = finder.castView(view, 2131558677, "field 'webView'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.webView = null;
  }
}
