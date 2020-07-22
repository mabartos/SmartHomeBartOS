import ChildCareIcon from '@material-ui/icons/ChildCare';
import PersonIcon from '@material-ui/icons/Person';
import SupervisorAccountIcon from '@material-ui/icons/SupervisorAccount';

export const Role = {
    ADMIN: {
        role: "HOME_ADMIN",
        name: "Admin",
        icon: SupervisorAccountIcon
    },
    MEMBER: {
        role: "HOME_MEMBER",
        name: "Member",
        icon: PersonIcon
    },
    CHILD: {
        role: "HOME_CHILD",
        name: "Child",
        icon: ChildCareIcon
    },
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

export const getRole = (role) => {
    switch (role) {
        case Role.ADMIN.role:
            return Role.ADMIN;
        case Role.MEMBER.role:
            return Role.MEMBER;
        case Role.CHILD.role:
        default:
            return Role.CHILD;
    }
};