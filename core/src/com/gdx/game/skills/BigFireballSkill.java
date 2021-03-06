package com.gdx.game.skills;

import com.gdx.game.skills.entities.bullets.SkillBullet;
import com.gdx.game.skills.entities.bullets.BigFireballSkillBullet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.entities.MortalEntity;
import com.gdx.game.entities.Player;
import com.gdx.game.factories.FilterFactory;
import java.util.Date;

/**
 *
 * @author ammanas
 */
public final class BigFireballSkill extends DamageSkillAdapter {

    private Date lastFireDate = null;

    public BigFireballSkill(float coolDown, MortalEntity caster, int damage, float speed, World world, float radius, Vector2 position) {
        super(coolDown, caster);
        this.setB(new BigFireballSkillBullet(damage, speed, radius, position));
    }

    @Override
    public void cast(Vector2 direction) {

        Date ts = new Date();

        if (lastFireDate == null || ts.getTime() - lastFireDate.getTime() > (getCoolDown() * 1000)) {

            Vector2 normDir = direction.nor();
            Vector2 bulletVelocity = new Vector2(normDir.x * getB().getInitalSpeed(), normDir.y * getB().getInitalSpeed());
            getB().setPosition(getCaster().getPosition().add(normDir.x * getCaster().getWidth() / 2, normDir.y * getB().getHeight() / 2));

            FilterFactory ff = new FilterFactory();

            if (getCaster() instanceof Player) {
                getB().setFilter(ff.getPlayerBulletFilter());
            } else {
                getB().setFilter(ff.getEnemyBulletFilter());
            }

            SkillBullet clone = this.getB().clone();

            getCaster().getParent().addActor(clone);
            clone.initPhysics();
            clone.initGraphics();

            clone.setLinearVelocity(bulletVelocity);

            lastFireDate = new Date();
        }

    }

    @Override
    public void castOn(MortalEntity caster) {
    }

}
