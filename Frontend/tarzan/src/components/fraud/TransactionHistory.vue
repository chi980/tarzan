<template>
  <div class="transaction-history">
    <table>
      <thead>
        <tr>
          <th>거래일</th>
          <th>거래액</th>
          <th>아파트</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(transaction, index) in transactions" :key="index">
          <td>{{ transaction.date }}</td>
          <td>{{ transaction.amount }}</td>
          <td>{{ transaction.apartment }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>
<script>
export default {
  data() {
    return {
      transactions: [
        { date: '23.11.10', amount: '9억 4000', apartment: '힐스테이트범어센트럴' },
        { date: '23.11.10', amount: '9억 4000', apartment: '힐스테이트범어센트럴' },
      ]
    };
  }
}
</script>
<style lang="scss" scoped>
  .transaction-history table {
  width: 100%;
  border-collapse: collapse;
  margin: 20px 0;
  }

  .transaction-history th, .transaction-history td {
    padding: 12px 15px;
    @include custom-text($font-size: 14px);
  }

  .transaction-history th {
    border-bottom: 1px solid #ddd;
    text-align: center;
  }

  .transaction-history td {
    // @include custom-text($font-size: 14px);
  }
</style>