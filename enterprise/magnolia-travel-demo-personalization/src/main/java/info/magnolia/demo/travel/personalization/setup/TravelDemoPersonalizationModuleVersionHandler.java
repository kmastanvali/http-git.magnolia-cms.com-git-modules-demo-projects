/**
 * This file Copyright (c) 2015 Magnolia International
 * Ltd.  (http://www.magnolia-cms.com). All rights reserved.
 *
 *
 * This program and the accompanying materials are made
 * available under the terms of the Magnolia Network Agreement
 * which accompanies this distribution, and is available at
 * http://www.magnolia-cms.com/mna.html
 *
 * Any modifications to this file must keep this entire header
 * intact.
 *
 */
package info.magnolia.demo.travel.personalization.setup;

import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.BootstrapSingleResource;
import info.magnolia.module.delta.DeltaBuilder;
import info.magnolia.module.delta.IsInstallSamplesTask;
import info.magnolia.module.delta.OrderNodeToFirstPositionTask;
import info.magnolia.module.delta.Task;
import info.magnolia.personalization.variant.VariantManager;
import info.magnolia.repository.RepositoryConstants;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.ImportUUIDBehavior;

/**
 * {@link DefaultModuleVersionHandler} for travel-demo personalization module.
 */
public class TravelDemoPersonalizationModuleVersionHandler extends DefaultModuleVersionHandler {

    public TravelDemoPersonalizationModuleVersionHandler() {
        register(DeltaBuilder.update("0.8", "")
                .addTask(new IsInstallSamplesTask("Re-Bootstrap website variants for travel pages", "Re-bootstrap website variants to account for all changes",
                        new BootstrapSingleResource("Re-Bootstrap variants", "", "/mgnl-bootstrap-samples/travel-demo-personalization/website.travel.variants.xml", ImportUUIDBehavior.IMPORT_UUID_COLLISION_REPLACE_EXISTING)))
        );
    }

    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {
        final List<Task> tasks = new ArrayList<Task>();

        tasks.addAll(super.getExtraInstallTasks(installContext));

        // Add variant mixin to travel page - so that adminCentral gives it the proper behaviour.
        tasks.add(new AddMixinTask("/travel", RepositoryConstants.WEBSITE, VariantManager.HAS_VARIANT_MIXIN));
        tasks.add(new OrderNodeToFirstPositionTask("Order travel page variants to first position.", "", RepositoryConstants.WEBSITE, "travel/variants"));

        return tasks;
    }

}