package me.trambled.ozark.ozarkclient.module.render;

import me.trambled.ozark.ozarkclient.event.events.EventRender;
import me.trambled.ozark.ozarkclient.module.Category;
import me.trambled.ozark.ozarkclient.module.Module;
import me.trambled.ozark.ozarkclient.module.Setting;
import me.trambled.ozark.ozarkclient.util.RenderUtil;
import me.trambled.turok.draw.RenderHelp;

/**
 * @author linustouchtips
 * @since 11/30/2020
 */

public class BreakESP extends Module {

    public BreakESP() {
        super(Category.RENDER);

        this.name = "Break ESP";
        this.tag = "BreakESP";
        this.description = "highlights blocks being broken";

    }

    Setting render_mode = create("Render Mode", "BreakESPRenderMode", "Pretty", combobox("Pretty", "Solid", "Outline", "None"));
    Setting info_mode = create("Info Mode", "BreakESPInfoMode", "Both", combobox( "Both", "Name", "Percent", "None"));
    Setting range = create("Range", "BreakESPRange", 6, 1, 250);
    Setting r = create("R", "BreakESPR", 0, 0, 255);
    Setting g = create("G", "BreakESPG", 255, 0, 255);
    Setting b = create("B", "BreakESPB", 0, 0, 255);
    Setting a = create("A", "BreakESPA", 50, 0, 255);

    boolean outline = false;
    boolean solid   = false;

    @Override
    public void render(EventRender event) {
        if (render_mode.in("Pretty")) {
            outline = true;
            solid   = true;
        }

        if (render_mode.in("Solid")) {
            outline = false;
            solid   = true;
        }

        if (render_mode.in("Outline")) {
            outline = true;
            solid   = false;
        }

        if (render_mode.in("None")) {
            outline = false;
            solid = false;
        }

        mc.renderGlobal.damagedBlocks.forEach((integer, destroyBlockProgress) -> {
            if (destroyBlockProgress.getPosition().getDistance((int) mc.player.posX,(int)  mc.player.posY,(int)  mc.player.posZ) <= range.get_value(1)) {
                if (solid) {
                    RenderHelp.prepare("quads");
                    RenderHelp.draw_cube(RenderHelp.get_buffer_build(),
                            destroyBlockProgress.getPosition().getX(), destroyBlockProgress.getPosition().getY(), destroyBlockProgress.getPosition().getZ(),
                            1, 1, 1,
                            r.get_value(1), g.get_value(1), b.get_value(1), a.get_value(1),
                            "all"
                    );

                    RenderHelp.release();
                }

                if (outline) {
                    RenderHelp.prepare("lines");
                    RenderHelp.draw_cube_line(RenderHelp.get_buffer_build(),
                            destroyBlockProgress.getPosition().getX(), destroyBlockProgress.getPosition().getY(), destroyBlockProgress.getPosition().getZ(),
                            1, 1, 1,
                            r.get_value(1), g.get_value(1), b.get_value(1), a.get_value(1),
                            "all"
                    );

                    RenderHelp.release();
                }

                if (info_mode.in("Name")) {
                    if (mc.world.getEntityByID(integer) != null) {
                        RenderUtil.drawText(destroyBlockProgress.getPosition(), mc.world.getEntityByID(integer).getName());
                    }
                }

                if (info_mode.in("Both")) {
                    if (mc.world.getEntityByID(integer) != null) {
                        RenderUtil.drawText(destroyBlockProgress.getPosition(), 0.6f, mc.world.getEntityByID(integer).getName());
                        RenderUtil.drawText(destroyBlockProgress.getPosition(), 0.2f,(destroyBlockProgress.getPartialBlockDamage() * 10) + "%");
                    }
                    RenderUtil.drawText(destroyBlockProgress.getPosition(), 0.2f,(destroyBlockProgress.getPartialBlockDamage() * 10) + "%");
                }

                if (info_mode.in("Percent")) {
                    RenderUtil.drawText(destroyBlockProgress.getPosition(), (destroyBlockProgress.getPartialBlockDamage() * 10) + "%");
                }


            }
        });
    }
}

