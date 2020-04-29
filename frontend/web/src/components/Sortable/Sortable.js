import {arrayMove, SortableContainer, SortableElement} from "react-sortable-hoc";
import makeStyles from "@material-ui/core/styles/makeStyles";
import React, {useState} from "react";
import avatar from "assets/img/faces/marc.jpg";


const useStyle = makeStyles(style => ({
    container: {
        display: "flex",
        flexWrap: "wrap",
        width: "100%",
        padding: "24px 12px 64px",
        margin: "10px",
        overflowY: "auto",
    },
    item: {
        display: "flex",
        maxWidth: "25%",
        position: "relative",
        flexBasis: " 100%",
        borderRadius: "2px",
        flexDirection: "column",
    },
    itemIMG: {
        maxWidth: "400px",
        maxHeight: "400px"
    },
    innerItem: {
        margin: "10px",
        display: "flex",
        maxWidth: "100%",
        maxHeight: "100%",
        alignItems: "center",
        flexDirection: "row",
        justifyContent: "center",
    },
}));

export default function Sortable(props) {
    const style = useStyle();
    const [state, setState] = useState(['Item 1', 'Item 2', 'Item 3', 'Item 4', 'Item 5']);

    const SortableItem = SortableElement(({item, children}) => (
        <div className={style.item}>
            <div className={style.innerItem}>
                {children}
            </div>
        </div>
    ));

    const SortableList = SortableContainer(({items}) => (
        <div className={style.container}>
            {items.map((item, index) => (
                <SortableItem
                    key={`${item.id}`}
                    index={index}
                    item={item}
                >
                    <img className={style.itemIMG} src={avatar} alt={"Avatar"}/>
                </SortableItem>
            ))}
        </div>
    ));


    const onSortEnd = ({oldIndex, newIndex}) => {
        setState(arrayMove(state, oldIndex, newIndex));
    };

    return (
        <SortableList
            items={state}
            onSortEnd={onSortEnd}
            axis="xy"
        />
    )
}