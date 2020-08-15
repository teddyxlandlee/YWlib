package bilibili.ywsuoyi.obj;


import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

import java.io.File;
import java.util.Scanner;

public class objmodelpart extends ModelPart {
    public final ObjectList<objQuad> objQuads = new ObjectArrayList<>();
    public final ObjectList<objvertex> objvertexs = new ObjectArrayList<>();
    public final ObjectList<uv> uvs = new ObjectArrayList<>();
    public final ObjectList<Vector3f> normal = new ObjectArrayList<>();
    public final ObjectList<objface> face = new ObjectArrayList<>();

    public objmodelpart(Model model) {
        super(model);
    }

    public objmodelpart(Model model, int textureOffsetU, int textureOffsetV) {
        super(model, textureOffsetU, textureOffsetV);
    }

    public objmodelpart(int textureWidth, int textureHeight, int textureOffsetU, int textureOffsetV) {
        super(textureWidth, textureHeight, textureOffsetU, textureOffsetV);
    }

    public void loadobj(String path, boolean a) {
        String[] line;
        try {
            System.out.println("loading"+this.getClass().getResource(path).getFile());
            File objfile = new File(this.getClass().getResource(path).getFile());

            Scanner reader = new Scanner(objfile);
            while (reader.hasNext()) {
                line = reader.nextLine().split(" ");
                switch (line[0]) {
                    case "v":
                        objvertexs.add(new objvertex(Float.parseFloat(line[1]), Float.parseFloat(line[2]), Float.parseFloat(line[3])));
                        break;
                    case "vt":
                        uvs.add(new uv(Float.parseFloat(line[1]), Float.parseFloat(line[2])));
                        break;
                    case "vn":
                        normal.add(new Vector3f(Float.parseFloat(line[1]), Float.parseFloat(line[2]), Float.parseFloat(line[3])));
                        break;
                    case "f":
                        face.add(new objface(line));
                        break;
                    default:
                        break;
                }
            }
            for (objface f : face) {
                objQuads.add(new objQuad(
                        new objvertex[]{
                                objvertexs.get(f.face[0].v),
                                objvertexs.get(f.face[1].v),
                                objvertexs.get(f.face[2].v),
                                objvertexs.get(f.face[3].v)},
                        a? normal.get(f.face[0].n):new Vector3f(0,0,0),
                        new uv[]{uvs.get(f.face[0].uv), uvs.get(f.face[1].uv), uvs.get(f.face[2].uv), uvs.get(f.face[3].uv)}
                ));
            }
            System.out.println(this.getClass().getResource(path).getFile()+"successful loaded");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (this.visible) {
            if (!this.objQuads.isEmpty()) {
                matrices.push();
                this.rotate(matrices);
                this.renderCuboids(matrices.peek(), vertexConsumer, light, overlay, red, green, blue, alpha);
                matrices.pop();
            }
        }
    }

    private void renderCuboids(MatrixStack.Entry matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        Matrix4f matrix4f = matrices.getModel();
        Matrix3f matrix3f = matrices.getNormal();
        for (objQuad quad : this.objQuads) {
            Vector3f vector3f = quad.direction.copy();
            vector3f.transform(matrix3f);
            float f = vector3f.getX();
            float g = vector3f.getY();
            float h = vector3f.getZ();

            for (int i = 0; i < 4; ++i) {
                objvertex vertex = quad.vertices[i];
                float j = vertex.pos.getX() / 16.0F;
                float k = vertex.pos.getY() / 16.0F;
                float l = vertex.pos.getZ() / 16.0F;
                Vector4f vector4f = new Vector4f(j, k, l, 1.0F);
                vector4f.transform(matrix4f);
                vertexConsumer.vertex(vector4f.getX(), vector4f.getY(), vector4f.getZ(), red, green, blue, alpha, quad.uv[i].u, quad.uv[i].v, overlay, light, f, g, h);
            }

        }

    }

    @Environment(EnvType.CLIENT)
    public static class objvertex {
        public final Vector3f pos;

        public objvertex(float x, float y, float z) {
            this(new Vector3f(x, y, z));
        }

        public objvertex(Vector3f vector3f) {
            this.pos = vector3f;
        }
    }

    @Environment(EnvType.CLIENT)
    public static class objQuad {
        public final objvertex[] vertices;
        public final uv[] uv;
        public final Vector3f direction;

        public objQuad(objvertex[] vertices, Vector3f normal, uv[] uv) {
            this.vertices = vertices;
            this.uv = uv;
            this.direction = normal;
        }
    }

    public static class uv {
        public float u;
        public float v;
        public uv(float u,float v){
            this.u=u;
            this.v=v;
        }
    }
    public static class objface {
        public final facepoint[] face = new facepoint[4];

        public objface(String[] face) {
            this.face[0] = new facepoint(face[1].split("/"));
            this.face[1] = new facepoint(face[2].split("/"));
            this.face[2] = new facepoint(face[3].split("/"));
            if(face.length<5){
                this.face[3] = this.face[2];
            }else {
                this.face[3] = new facepoint(face[4].split("/"));
            }
        }
        public static class facepoint {
            public facepoint(int v,int n, int uv){
                this.v=v;
                this.n=n;
                this.uv=uv;
            }
            public facepoint(String...i){
                this.v=Integer.parseInt(i[0])-1;
                this.uv=Integer.parseInt(i[1])-1;
                this.n=Integer.parseInt(i[2])-1;
            }

            public int v;
            public int uv;
            public int n;
        }
    }
}
