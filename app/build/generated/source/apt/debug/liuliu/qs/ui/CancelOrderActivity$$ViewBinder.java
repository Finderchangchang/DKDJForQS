// Generated code from Butter Knife. Do not modify!
package liuliu.qs.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CancelOrderActivity$$ViewBinder<T extends liuliu.qs.ui.CancelOrderActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558524, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131558524, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131558596, "field 'coCb1'");
    target.coCb1 = finder.castView(view, 2131558596, "field 'coCb1'");
    view = finder.findRequiredView(source, 2131558597, "field 'coCb2'");
    target.coCb2 = finder.castView(view, 2131558597, "field 'coCb2'");
    view = finder.findRequiredView(source, 2131558598, "field 'coCb3'");
    target.coCb3 = finder.castView(view, 2131558598, "field 'coCb3'");
    view = finder.findRequiredView(source, 2131558599, "field 'coCb4'");
    target.coCb4 = finder.castView(view, 2131558599, "field 'coCb4'");
    view = finder.findRequiredView(source, 2131558600, "field 'coContent'");
    target.coContent = finder.castView(view, 2131558600, "field 'coContent'");
    view = finder.findRequiredView(source, 2131558601, "field 'btnCoBack'");
    target.btnCoBack = finder.castView(view, 2131558601, "field 'btnCoBack'");
    view = finder.findRequiredView(source, 2131558602, "field 'btnCoCancel'");
    target.btnCoCancel = finder.castView(view, 2131558602, "field 'btnCoCancel'");
    view = finder.findRequiredView(source, 2131558595, "field 'total_rg'");
    target.total_rg = finder.castView(view, 2131558595, "field 'total_rg'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.coCb1 = null;
    target.coCb2 = null;
    target.coCb3 = null;
    target.coCb4 = null;
    target.coContent = null;
    target.btnCoBack = null;
    target.btnCoCancel = null;
    target.total_rg = null;
  }
}
