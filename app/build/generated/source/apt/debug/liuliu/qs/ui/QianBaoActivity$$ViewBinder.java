// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class QianBaoActivity$$ViewBinder<T extends liuliu.qs.ui.QianBaoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'title_bar'");
    target.title_bar = finder.castView(view, 2131558524, "field 'title_bar'");
    view = finder.findRequiredView(source, 2131558535, "field 'yeTv'");
    target.yeTv = finder.castView(view, 2131558535, "field 'yeTv'");
    view = finder.findRequiredView(source, 2131558536, "field 'mxTv'");
    target.mxTv = finder.castView(view, 2131558536, "field 'mxTv'");
    view = finder.findRequiredView(source, 2131558537, "field 'czTv'");
    target.czTv = finder.castView(view, 2131558537, "field 'czTv'");
    view = finder.findRequiredView(source, 2131558538, "field 'czjlLl'");
    target.czjlLl = finder.castView(view, 2131558538, "field 'czjlLl'");
  }

  @Override public void unbind(T target) {
    target.title_bar = null;
    target.yeTv = null;
    target.mxTv = null;
    target.czTv = null;
    target.czjlLl = null;
  }
}
