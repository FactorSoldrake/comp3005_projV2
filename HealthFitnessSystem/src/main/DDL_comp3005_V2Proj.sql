
-- Table clearing
DROP TABLE IF EXISTS Fitness_Achievement;
DROP TABLE IF EXISTS Fitness_Goal;
DROP TABLE IF EXISTS Health_Metrics;
DROP TABLE IF EXISTS Trainer_Availability;
DROP TABLE IF EXISTS Member_Routine;
DROP TABLE IF EXISTS Private_Class;
DROP TABLE IF EXISTS Group_Class;
DROP TABLE IF EXISTS Billing;
DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS Trainer;
DROP TABLE IF EXISTS Equipment_Track;
DROP TABLE IF EXISTS Room_Track;


-- Member Table
CREATE TABLE Member (
    member_ID   SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    pass        VARCHAR(255) NOT NULL
);

-- Admin Table
CREATE TABLE Admin (
    admin_ID   SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    pass        VARCHAR(255) NOT NULL
);

-- Trainer Table
CREATE TABLE Trainer (
    trainer_ID   SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    pass        VARCHAR(255) NOT NULL
);


-- Fitness Goals (Member)
CREATE TABLE Fitness_Goal(
    goal_ID     SERIAL PRIMARY KEY,
    member_ID   INTEGER REFERENCES Member(member_ID) ON DELETE CASCADE,
    describe    TEXT, 
    goal_comp   BOOLEAN DEFAULT FALSE
);


-- Health Metrics (Member and Trainer)
CREATE TABLE Health_Metrics(
    stat_ID      SERIAL PRIMARY KEY,
    member_ID    INTEGER REFERENCES Member(member_ID) ON DELETE CASCADE,
    height       INTEGER CHECK (height > 0), --cm
    weight       INTEGER CHECK (weight > 0), --kg
    age          INTEGER CHECK (age > 0),
    updated_info DATE DEFAULT CURRENT_DATE
);


-- Fitness achievements (Member and Trainer)
CREATE TABLE Fitness_Achievement(
    achieve_ID   SERIAL PRIMARY KEY,
    goal_ID      INTEGER NOT NULL UNIQUE REFERENCES Fitness_Goal(goal_ID) ON DELETE CASCADE,
    achieve_date DATE
);

-- Member Routines (Member and Trainer)
CREATE TABLE Member_Routine(
    exercise_ID  SERIAL PRIMARY KEY,
    member_ID    INTEGER REFERENCES Member(member_ID) ON DELETE CASCADE,
    routine_name VARCHAR(255) NOT NULL,
    description  TEXT
);


-- Trainer's time (Trainer)
CREATE TABLE Trainer_Availability(
    time_ID     SERIAL PRIMARY KEY,
    trainer_ID  INTEGER REFERENCES Trainer(trainer_ID) ON DELETE CASCADE,
    avail_from  TIME NOT NULL CHECK (avail_from < avail_to),
    avail_to    TIME NOT NULL CHECK (avail_from < avail_to)
);


-- Equipment Maintainence tracker (Admin)
CREATE TABLE Equipment_Track (
    equipment_ID  SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    maintain_last TIMESTAMP NOT NULL CHECK (maintain_last <= maintain_next),
    maintain_next TIMESTAMP NOT NULL CHECK (maintain_last <= maintain_next),
    condition     TEXT
);

-- Holds room information (Admin)
CREATE TABLE Room_Track (
    room_ID         SERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    capacity        INTEGER NOT NULL CHECK (capacity > 0),
    room_status     VARCHAR(20) DEFAULT 'Available',
    last_maintain   DATE CHECK (last_maintain <= next_maintain),
    next_maintain   DATE CHECK (last_maintain <= next_maintain)
);

-- Private class booking (Member, Admin, Trainer)
CREATE TABLE Private_Class (
    privClass_ID    SERIAL PRIMARY KEY,
    member_ID       INTEGER NOT NULL REFERENCES Member(member_ID) ON DELETE CASCADE,
    trainer_ID      INTEGER NOT NULL REFERENCES Trainer(trainer_ID) ON DELETE CASCADE,
    room_ID         INTEGER NOT NULL REFERENCES Room_Track(room_ID) ON DELETE CASCADE,
    start_time      TIMESTAMP NOT NULL CHECK (start_time < end_time),
    end_time        TIMESTAMP NOT NULL CHECK (start_time < end_time),
    cost            DECIMAL NOT NULL DEFAULT 80 CHECK (cost > 0),
    comments        TEXT
);

-- Group Class (Member, Admin, Trainer)
CREATE TABLE Group_Class (
    groupClass_ID   SERIAL PRIMARY KEY,
    trainer_ID      INTEGER NOT NULL REFERENCES Trainer(trainer_ID) ON DELETE CASCADE,
    room_ID         INTEGER NOT NULL REFERENCES Room_Track(room_ID) ON DELETE CASCADE,
    start_time      TIMESTAMP NOT NULL CHECK (start_time < end_time),
    end_time        TIMESTAMP NOT NULL CHECK (start_time < end_time),
    cost            DECIMAL NOT NULL DEFAULT 20 CHECK (cost > 0),
    comments        TEXT,
    total_capacity  INTEGER NOT NULL CHECK (total_capacity > 0),
    capacity_remain INTEGER NOT NULL CHECK (capacity_remain <= total_capacity AND 0 <= capacity_remain)
);



-- Billing table (Admin, Trainer, Member)
CREATE TABLE Billing (
    billing_ID       SERIAL PRIMARY KEY,
    fees             DECIMAL CHECK (fees > 0),
    issue_date       DATE CHECK (issue_date <= due_date),
    due_date         DATE CHECK (issue_date <= due_date),
    payment_status   VARCHAR(20) DEFAULT 'Issued',
    class_type       VARCHAR(20),
    product_descript TEXT,
    member_ID        INTEGER NOT NULL REFERENCES Member(member_ID)
);



