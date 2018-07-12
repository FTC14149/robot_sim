package com.example.rhill.controllertest3;

import android.graphics.Bitmap;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

/**
 * Created by rhill on 5/6/18.
 */

public class Robot extends MovableObject implements ContactListener {
    World world;
    public float speed;
    Vec2 leftwheel = new Vec2(-1.0f,1.0f);  // Location of left wheel in body coordinates
    Vec2 rightwheel = new Vec2(1.0f,1.0f);  // Location of right wheel in body coordinates
    Vec2 force = new Vec2(0.0f, 1.0f);
    Body robotBody;
    Body leftWheel;
    Body rightWheel;
    Body frontSensor;
    boolean frontSensorTouching;
    Body lastTouchedBody;
    public Robot(World world, Body body, Bitmap bitmap, float x, float y) {
        super(body,bitmap);
        this.world = world;
        float robotRadius = 0.19f/100.0f;
        float robotInitialPosition = 1.1f;
        // Robot body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set(x,y);
        bodyDef.setAngle(35 * 3.14f / 180f);
        bodyDef.linearDamping = 0.01f;
        bodyDef.angularDamping = 0.01f;
        robotBody = world.createBody(bodyDef);

        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(0.38f/2, 0.42f/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.setDensity(0.001f);
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.3f;
        robotBody.createFixture(fixtureDef);

        super.body = robotBody;

        BodyDef leftWheelDef = new BodyDef();
        leftWheelDef.type = BodyType.DYNAMIC;
        leftWheelDef.position.set(robotInitialPosition-robotRadius,robotInitialPosition+robotRadius);
        leftWheelDef.linearDamping = 10000000.0f;
        leftWheelDef.angularDamping = 100.0f;
        leftWheel = world.createBody(leftWheelDef);
        PolygonShape leftWheelBox = new PolygonShape();
        leftWheelBox.setAsBox(0.01f/2, 0.01f/2);
        FixtureDef leftWheelFixtureDef = new FixtureDef();
        leftWheelFixtureDef.shape = leftWheelBox;
        leftWheelFixtureDef.setDensity(1);
        leftWheelFixtureDef.friction = 0.3f;
        leftWheelFixtureDef.restitution = 0.3f;
        leftWheel.createFixture(leftWheelFixtureDef);

        WeldJointDef leftWheelJointDef = new WeldJointDef();
        leftWheelJointDef.bodyA = leftWheel;
        leftWheelJointDef.localAnchorA.x = 0.0f;
        leftWheelJointDef.localAnchorA.y = 0.0f;
        leftWheelJointDef.bodyB = robotBody;
        leftWheelJointDef.localAnchorB.x = -0.19f;
        leftWheelJointDef.localAnchorB.y = 0.19f;
        leftWheelJointDef.collideConnected = false;
        WeldJoint leftWheelJoint = (WeldJoint) world.createJoint(leftWheelJointDef);

        BodyDef rightWheelDef = new BodyDef();
        rightWheelDef.type = BodyType.DYNAMIC;
        rightWheelDef.position.set(robotInitialPosition+robotRadius,robotInitialPosition+robotRadius);
        rightWheelDef.linearDamping = 10000000.0f;
        rightWheelDef.angularDamping = 100.0f;
        rightWheel = world.createBody(rightWheelDef);

        PolygonShape rightWheelBox = new PolygonShape();
        rightWheelBox.setAsBox(0.01f/2, 0.01f/2);
        FixtureDef rightWheelFixtureDef = new FixtureDef();
        rightWheelFixtureDef.shape = rightWheelBox;
        rightWheelFixtureDef.setDensity(1);
        rightWheelFixtureDef.friction = 0.3f;
        rightWheelFixtureDef.restitution = 0.3f;
        rightWheel.createFixture(rightWheelFixtureDef);

        WeldJointDef rightWheelJointDef = new WeldJointDef();
        rightWheelJointDef.bodyA = rightWheel;
        rightWheelJointDef.bodyB = robotBody;
        rightWheelJointDef.localAnchorA.x = 0.0f;
        rightWheelJointDef.localAnchorA.y = 0.0f;
        rightWheelJointDef.bodyB = robotBody;
        rightWheelJointDef.localAnchorB.x = 0.19f;
        rightWheelJointDef.localAnchorB.y = 0.19f;
        rightWheelJointDef.collideConnected = false;
        WeldJoint rightWheelJoint = (WeldJoint) world.createJoint(rightWheelJointDef);

        BodyDef frontSensorDef = new BodyDef();
        frontSensorDef.type = BodyType.DYNAMIC;
        frontSensorDef.position.set(robotInitialPosition,robotInitialPosition-robotRadius-0.02f);
        frontSensorDef.linearDamping = 10000000.0f;
        frontSensorDef.angularDamping = 100.0f;
        frontSensor = world.createBody(frontSensorDef);

        PolygonShape frontSensorBox = new PolygonShape();
        frontSensorBox.setAsBox(0.01f/2, 0.01f/2);
        FixtureDef frontSensorFixtureDef = new FixtureDef();
        frontSensorFixtureDef.shape = frontSensorBox;
        frontSensorFixtureDef.setDensity(1);
        frontSensorFixtureDef.friction = 0.3f;
        frontSensorFixtureDef.restitution = 0.3f;
        frontSensor.createFixture(frontSensorFixtureDef);

        WeldJointDef frontSensorJointDef = new WeldJointDef();
        frontSensorJointDef.bodyA = frontSensor;
        frontSensorJointDef.bodyB = robotBody;
        frontSensorJointDef.localAnchorA.x = 0.0f;
        frontSensorJointDef.localAnchorA.y = 0.0f;
        frontSensorJointDef.bodyB = robotBody;
        frontSensorJointDef.localAnchorB.x = 0.0f;
        frontSensorJointDef.localAnchorB.y = -0.21f;
        frontSensorJointDef.collideConnected = false;
        WeldJoint frontSensorJoint = (WeldJoint) world.createJoint(frontSensorJointDef);

        x = 200;
        y = 200;
        rot = 0;
        speed = 1;
        world.setContactListener(this);
    }
    public void ApplyForce(Vec2 force, Vec2 point) {
        //body.applyForce(body.getWorldVector(force),body.getWorldPoint(point));
        body.applyForce(body.getWorldVector(force),body.getWorldPoint(point));
    }
    public void LeftMotorTorque(float t) {
        force.y = t*1000.0f;
        leftWheel.applyForce(leftWheel.getWorldVector(force),leftWheel.getWorldCenter());
    }
    public void RightMotorTorque(float t) {
        force.y = t*1000.0f;
        rightWheel.applyForce(rightWheel.getWorldVector(force),rightWheel.getWorldCenter());
    }
    public void LeftMotorImpulse(float i) {
        force.y = i*30.0f;
        leftWheel.applyLinearImpulse(leftWheel.getWorldVector(force),leftWheel.getWorldCenter(),true);
    }
    public void RightMotorImpulse(float i) {
        force.y = i*30.0f;
        rightWheel.applyLinearImpulse(rightWheel.getWorldVector(force),rightWheel.getWorldCenter(),true);
    }
    public boolean FrontSensorTouching() {
        return frontSensorTouching;
    }
    public void EnableMagnet(boolean enable) {
        if(enable) {
            if(lastTouchedBody != null) {
                Vec2 magnetVec = frontSensor.getWorldCenter().sub(lastTouchedBody.getWorldCenter());
                if(magnetVec.length() < 0.3f) {
                    lastTouchedBody.applyForceToCenter(magnetVec.mul(30.0f));
                }
            }
        }
    }
    public int Compass() {
        return (((int)(57.295f*body.getAngle()) % 360) + 360) % 360;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a.getBody().equals(frontSensor)) {
            frontSensorTouching = true;
            lastTouchedBody = b.getBody();
        }
        if(b.getBody().equals(frontSensor)) {
            frontSensorTouching = true;
            lastTouchedBody = a.getBody();
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();
        if(a.getBody().equals(frontSensor) || b.getBody().equals(frontSensor)) {
            frontSensorTouching = false;
            //lastTouchedBody = null;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}
