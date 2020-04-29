import React, {useState} from "react";


export default function ListWrapper(props) {

    const [items, setItems] = useState();
    const [isSorting, setIsSorting] = useState();

    const onSortStart = (sortEvent, nativeEvent) => {
        const {onSortStart} = props;
        setIsSorting(true);

        document.body.style.cursor = 'grabbing';

        if (onSortStart) {
            onSortStart(sortEvent, nativeEvent, this.refs.component);
        }
    };

    const onSortEnd = (sortEvent, nativeEvent) => {
        const {onSortEnd} = this.props;
        const {oldIndex, newIndex} = sortEvent;
        const {items} = this.state;

        this.setState({
            items: arrayMove(items, oldIndex, newIndex),
            isSorting: false,
        });

        document.body.style.cursor = '';

        if (onSortEnd) {
            onSortEnd(sortEvent, nativeEvent, this.refs.component);
        }
    };


}