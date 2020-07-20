import React from 'react';
import PropTypes from "prop-types";

export default function Conditional(props) {
    const {condition, children} = props;
    return (
        <>
            {condition && children}
        </>
    )
}

Conditional.propTypes = {
    condition: PropTypes.bool,
    children: PropTypes.node
};
