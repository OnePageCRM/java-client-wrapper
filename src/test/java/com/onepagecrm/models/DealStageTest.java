package com.onepagecrm.models;

import com.onepagecrm.BaseTest;
import com.onepagecrm.models.fabricators.UserFabricator;
import com.onepagecrm.models.internal.DealStage;

import java.util.List;

/**
 * Created by Cillian Myles <cillian@onepagecrm.com> on 05/08/2016.
 */
public class DealStageTest extends BaseTest {

    private List<DealStage> stages;
    private DealStage won;
    private DealStage lost;
    private DealStage decision50;
    private DealStage none25;

    @SuppressWarnings("AccessStaticViaInstance")
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Set up needed resources.
        stages = DealStage.addDefaults(UserFabricator.single().account.settings.getDealStages());
        won = DealStage.won();
        lost = DealStage.lost();
        decision50 = new DealStage().setLabel("Decision").setPercentage(50);
        none25 = new DealStage().setLabel(null).setPercentage(25);
    }

    /**
     * Should generate the same String as the contents of the text file.
     */
    public void testDealStageCompare() {
        assertTrue("Stage list should contain stage : \'" + decision50 + "\'", stages.contains(decision50));
        assertTrue("Stage list should contain stage : \'" + none25 + "\'", stages.contains(none25));
        assertTrue("Stage list should contain stage : \'" + won + "\'", stages.contains(won));
        assertTrue("Stage list should contain stage : \'" + lost + "\'", stages.contains(lost));
    }
}
