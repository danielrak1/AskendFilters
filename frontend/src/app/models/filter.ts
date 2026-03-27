export interface Filter {
    id?: number;
    name: string;
    criteriaList: Criteria[];
}

export interface Criteria {
    id?: number;
    type: 'AMOUNT' | 'TITLE' | 'DATE';
    criteria_condition: string;
    criteria_value: string;
    
}
