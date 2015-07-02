package com.pmease.gitplex.web;

import java.util.Collection;

import org.apache.tika.mime.MediaType;
import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;
import org.pegdown.Parser;
import org.pegdown.plugins.ToHtmlSerializerPlugin;

import com.google.common.collect.Lists;
import com.pmease.commons.git.extensionpoint.TextConverter;
import com.pmease.commons.git.extensionpoint.TextConverterProvider;
import com.pmease.commons.jetty.ServletConfigurator;
import com.pmease.commons.loader.AbstractPluginModule;
import com.pmease.commons.markdown.extensionpoint.HtmlTransformer;
import com.pmease.commons.markdown.extensionpoint.MarkdownExtension;
import com.pmease.commons.wicket.AbstractWicketConfig;
import com.pmease.commons.wicket.editable.EditSupport;
import com.pmease.gitplex.core.listeners.PullRequestListener;
import com.pmease.gitplex.core.manager.UrlManager;
import com.pmease.gitplex.core.validation.UserNameReservation;
import com.pmease.gitplex.search.IndexListener;
import com.pmease.gitplex.web.avatar.AvatarManager;
import com.pmease.gitplex.web.avatar.DefaultAvatarManager;
import com.pmease.gitplex.web.component.blobview.BlobRenderer;
import com.pmease.gitplex.web.component.comment.MentionTransformer;
import com.pmease.gitplex.web.editable.EditSupportLocator;
import com.pmease.gitplex.web.extensionpoint.DiffRenderer;
import com.pmease.gitplex.web.extensionpoint.DiffRendererProvider;
import com.pmease.gitplex.web.extensionpoint.MediaRenderer;
import com.pmease.gitplex.web.extensionpoint.MediaRendererProvider;
import com.pmease.gitplex.web.page.repository.file.RepoFilePage;
import com.pmease.gitplex.web.page.repository.pullrequest.RequestDetailPage;

/**
 * NOTE: Do not forget to rename moduleClass property defined in the pom if you've renamed this class.
 *
 */
public class WebModule extends AbstractPluginModule {

	@Override
	protected void configure() {
		super.configure();
		
		// put your guice bindings here
		bind(AbstractWicketConfig.class).to(WicketConfig.class);		
		bind(WebApplication.class).to(WicketConfig.class);
		bind(Application.class).to(WicketConfig.class);
		bind(AvatarManager.class).to(DefaultAvatarManager.class);
		
		contribute(ServletConfigurator.class, WebServletConfigurator.class);
		contribute(UserNameReservation.class, WebUserNameReservation.class);
		
		contributeFromPackage(EditSupport.class, EditSupportLocator.class);
		
		contribute(PullRequestListener.class, RequestDetailPage.Updater.class);
		contribute(IndexListener.class, RepoFilePage.IndexedListener.class);
		
		contribute(MediaRendererProvider.class, new MediaRendererProvider() {

			@Override
			public MediaRenderer getMediaRenderer(MediaType mediaType) {
				return null;
			}
			
		});
		
		contribute(DiffRendererProvider.class, new DiffRendererProvider() {

			@Override
			public DiffRenderer getDiffRenderer(MediaType mediaType) {
				return null;
			}
			
		});
		
		contribute(TextConverterProvider.class, new TextConverterProvider() {

			@Override
			public TextConverter getTextConverter(MediaType mediaType) {
				return null;
			}

		});
		
		contributeFromPackage(BlobRenderer.class, BlobRenderer.class);
		
		contribute(MarkdownExtension.class, new MarkdownExtension() {
			
			@Override
			public Collection<Class<? extends Parser>> getInlineParsers() {
				return null;
			}
			
			@Override
			public Collection<ToHtmlSerializerPlugin> getHtmlSerializers() {
				return null;
			}
			
			@Override
			public Collection<Class<? extends Parser>> getBlockParsers() {
				return null;
			}

			@Override
			public Collection<HtmlTransformer> getHtmlTransformers() {
				return Lists.newArrayList((HtmlTransformer)new MentionTransformer());
			}
			
		});

		bind(UrlManager.class).to(WebUrlManager.class);
	}
	
}
