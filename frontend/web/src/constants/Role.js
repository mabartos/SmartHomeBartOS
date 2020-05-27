export const Role = {
    ADMIN: {role: "HOME_ADMIN", name: "Admin"},
    MEMBER: {role: "HOME_MEMBER", name: "Member"},
    CHILD: {role: "HOME_CHILD", name: "Child"},
};

export const getRoleName = (role) => {
    switch (role) {
        case Role.ADMIN.role:
            return Role.ADMIN.name;
        case Role.MEMBER.role:
            return Role.MEMBER.name;
        case Role.CHILD.role:
        default:
            return Role.CHILD.name;
    }
};